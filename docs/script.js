document.addEventListener('DOMContentLoaded', function() {
    const fileTree = document.getElementById('file-tree');

    // Example data structure for the file explorer
    const fileStructure = {
        "docs": {
            "maven": {
                "th": {
                    "tamkungz": {
                        "droprateapi": {
                            "1.0.1-patch1": {
                                "droprateapi-1.0.1-patch1.jar": {},
                                "droprateapi-1.0.1-patch1.pom": {}
                            }
                        }
                    }
                }
            },
            "index.html": {}
        }
    };

    function createFileTree(fileStructure, parentElement) {
        const ul = document.createElement('ul');
        parentElement.appendChild(ul);

        for (const key in fileStructure) {
            if (fileStructure.hasOwnProperty(key)) {
                const li = document.createElement('li');

                if (typeof fileStructure[key] === 'object' && Object.keys(fileStructure[key]).length > 0) {
                    li.classList.add('folder');
                    li.textContent = key;
                    li.addEventListener('click', function() {
                        this.classList.toggle('open');
                        const childUl = this.querySelector('ul');
                        if (childUl) {
                            childUl.classList.toggle('hidden');
                        }
                    });
                    createFileTree(fileStructure[key], li);
                } else {
                    li.classList.add('file');
                    li.textContent = key;
                    li.addEventListener('click', function() {
                        alert('Opened file: ' + key);
                    });
                }

                ul.appendChild(li);
            }
        }
    }

    createFileTree(fileStructure, fileTree);
});
