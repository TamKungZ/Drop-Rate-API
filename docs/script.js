// กำหนดข้อมูลสำหรับ repository ของคุณ
const repoOwner = "TamKungZ";
const repoName = "droprateapi";
const branch = "1.16.5-Forge"; // branch ที่ GitHub Pages deploy
const basePath = "docs/maven"; // โฟลเดอร์ที่เก็บไฟล์ Maven

// สร้าง URL สำหรับดึง tree แบบ recursive
const apiUrl = `https://api.github.com/repos/${repoOwner}/${repoName}/git/trees/${branch}?recursive=1`;

// ฟังก์ชันเปลี่ยนรายการไฟล์จาก GitHub API ให้เป็น tree structure
function buildTree(files) {
  const tree = {};
  files.forEach(item => {
    // เราสนใจเฉพาะไฟล์ภายใน basePath
    if (item.path.startsWith(basePath)) {
      // เอา path ที่เหลือหลังจาก basePath
      const relativePath = item.path.substring(basePath.length).replace(/^\/+/, '');
      const parts = relativePath.split('/');
      let current = tree;
      parts.forEach((part, index) => {
        if (index === parts.length - 1) {
          // เป็นไฟล์หรือโฟลเดอร์สุดท้าย
          current[part] = item.type === 'tree' ? {} : null;
        } else {
          // สร้างโฟลเดอร์ถ้ายังไม่มี
          if (!current[part]) {
            current[part] = {};
          }
          current = current[part];
        }
      });
    }
  });
  return tree;
}

// ฟังก์ชันสร้าง HTML tree view จาก tree structure
function createFileTree(structure, parentElement, currentPath) {
  currentPath = currentPath || "";
  const ul = document.createElement('ul');
  parentElement.appendChild(ul);

  for (const key in structure) {
    if (structure.hasOwnProperty(key)) {
      const li = document.createElement('li');

      // ถ้าค่าเป็น object ให้ถือว่าเป็นโฟลเดอร์
      if (structure[key] && typeof structure[key] === 'object') {
        li.classList.add('folder');
        li.textContent = key;
        // เมื่อคลิกจะเปิด/ปิดโฟลเดอร์
        li.addEventListener('click', function(e) {
          e.stopPropagation();
          this.classList.toggle('open');
          const childUl = this.querySelector('ul');
          if (childUl) {
            childUl.classList.toggle('hidden');
          }
        });
        createFileTree(structure[key], li, currentPath + "/" + key);
      } else {
        // ถ้าเป็น null ให้ถือว่าเป็นไฟล์
        li.classList.add('file');
        const fileLink = document.createElement('a');
        // สร้างเส้นทางสำหรับไฟล์ (URL สำหรับดาวน์โหลด)
        let filePath = (basePath + "/" + currentPath + "/" + key)
          .replace(/\/+/g, '/')
          .replace(/^\/+/, '');
        // สร้าง URL จากชื่อโดเมน GitHub Pages ของคุณ
        let baseURL = window.location.origin + window.location.pathname;
        if (!baseURL.endsWith('/')) {
          baseURL += '/';
        }
        fileLink.href = baseURL + filePath;
        fileLink.textContent = key;
        fileLink.target = "_blank";
        li.appendChild(fileLink);
      }
      ul.appendChild(li);
    }
  }
}

// ดึงข้อมูลจาก GitHub API แล้วแสดงผล
fetch(apiUrl)
  .then(response => response.json())
  .then(data => {
    // data.tree จะเป็น array ของไฟล์ทั้งหมด
    const treeData = buildTree(data.tree);
    const fileTree = document.getElementById('file-tree');
    fileTree.innerHTML = ""; // clear loading text
    createFileTree(treeData, fileTree);
  })
  .catch(error => {
    console.error("Error fetching file tree:", error);
    document.getElementById('file-tree').textContent = "Failed to load file structure.";
  });
