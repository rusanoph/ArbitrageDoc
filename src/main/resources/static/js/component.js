
//#region On page startup / on load

document.addEventListener("DOMContentLoaded", function() {

    let backToTopElement = document.getElementById("backToTop");

    const scrollStart = 200;
    window.addEventListener("scroll", function() {
        if (document.body.scrollTop > scrollStart || document.documentElement.scrollTop > scrollStart) {
            backToTopElement.style.display = "block";
        } else {
            backToTopElement.style.display = "none";
        }
    });

    backToTopElement.addEventListener("click", function() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0
    });
});

//#endregion

function openTab(tabId, element, color) {
    
    const tabContents = document.getElementsByClassName("tab-content");
    for (let content of tabContents) {
        content.style.display = "none";
    }
    
    const tabLinks = document.getElementsByClassName("tab-link");
    for (let link of tabLinks) {
        link.style.backgroundColor = "";
    }

    document.getElementById(tabId).style.display = "block";
    
    element.style.backgroundColor = color;
}
document.getElementById("defaultTab").click();



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