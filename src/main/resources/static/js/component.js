


function treeViewToggler() {
    let treeViewToggler = document.getElementsByClassName("caret");
    for (let i = 0; i < treeViewToggler.length; i++) {

        treeViewToggler[i].addEventListener('click', function() {
            this.parentElement.querySelector(".nested-tree").classList.toggle("active-tree");
            this.classList.toggle("caret-down");
        });

    }
}

treeViewToggler();