
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

    let backToBottomElement = document.getElementById("backToBottom");

    const scrollEnd = 200;
    window.addEventListener("scroll", function() {
        let isHideBackToBottomDocElement = document.documentElement.scrollHeight - document.documentElement.clientHeight - scrollEnd < document.documentElement.scrollTop;
        let isHideBackToBottomBody = document.body.scrollHeight - document.body.clientHeight - scrollEnd < document.body.scrollTop;

        if (isHideBackToBottomDocElement || isHideBackToBottomBody) {
            backToBottomElement.style.display = "none";
        } else {
            backToBottomElement.style.display = "block";
        }
    });

    backToBottomElement.addEventListener("click", function() {
        document.body.scrollTop = document.body.scrollHeight;
        document.documentElement.scrollTop = document.documentElement.scrollHeight;
    })
});

//#endregion

//#region Copy to Clipboard

function copyToClipboard(elementObj) {
    navigator.clipboard.writeText(elementObj.innerText);

    alert("Текст скопирован в буфер обмена");
}

//#endregion

//#region Tab
function openTab(tabId, element, color) {
    
    const tabContents = document.getElementsByClassName("tab-content");
    for (let content of tabContents) {
        content.style.display = "none";
    }
    
    const tabLinks = document.getElementsByClassName("tab-link");
    for (let link of tabLinks) {
        link.style.backgroundColor = "";
    }

    const tabSubLinks = document.getElementsByClassName("tab-sub-link");
    for (let subLink of tabSubLinks) {
        subLink.style.backgroundColor = "";
    }

    // Parent tab-content
    const parentTabContent = findParent(element, ".tab-content");
    if (parentTabContent !== null) {
        document.getElementById(parentTabContent.id).style.display = "block";
    }

    document.getElementById(tabId).style.display = "block";
    
    element.style.backgroundColor = color;
}
if (document.getElementById("defaultTab") !== null) {
    document.getElementById("defaultTab").click();
}

function findParent(element, selector) {
    if (!element) return null;

    return element.matches && element.matches(selector) ? element : this.findParent(element.parentNode, selector);
}
//#endregion

//#region Tree View
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
//#endregion

//#region Table/List Sort Functions

function sortTable(elementId, columnIndex, i=1) {
    
    let table = document.getElementById(elementId);

    let isSwap = true;
    while (isSwap) {

        isSwap = false;
        let rows = table.rows;

        for (let i = 1; i < rows.length - 1; i++) {
            let doSwap = false;

            let x = rows[i].getElementsByTagName("td")[columnIndex];
            let y = rows[i + 1].getElementsByTagName("td")[columnIndex];

            if (isNaN(x.innerHTML)) {
                doSwap = x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase();
            } else {
                doSwap = parseInt(x.innerHTML) > parseInt(y.innerHTML);
            }

            if (doSwap) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                isSwap = true;
                break;
            }
        }

    }

}

function sortDivList(elementId, columnIndex, i=1) {
    
}

//#endregion

//#region Accordion

function initAccordion() {
    const displayStyle = "block";
    for (let accordion of accordions) {
        accordion.addEventListener('click', function() {
            accordion.classList.toggle("accordion-active");
            
            let panel = accordion.nextElementSibling;
            if (panel.style.display === displayStyle) {
                panel.style.display = "none";

                getAllChildrenDeep(panel).forEach(child => child.style.display = "none");

            } else {
                panel.style.display = displayStyle;

                getAllChildrenDeep(panel).forEach(child => child.style.display = displayStyle);

            }
        });
    }
}

function getAllChildrenDeep(element) {
    let children = [];

    if (element.children !== null) {
        for (let child of element.children) {
            children.push(child)
            
            children.concat(this.getAllChildrenDeep(child));
        }
    }

    return children;
}

let accordions = document.getElementsByClassName("accordion");
if (accordions.length > 0) {
    initAccordion();
}

//#endregion

//#region Table Pagination

function renderPageData(tableBodyId, data, pageSize, currentPage) {

    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = startIndex + pageSize;
    const pageData = data.slice(startIndex, endIndex);

    const tableBody = document.getElementById(tableBodyId);
    tableBody.innerHTML = '';

    pageData.forEach(rowData => {
        const row = document.createElement('tr');

        tableBody.appendChild(row);
    });
}
//#endregion

//#region Modal
function modalOpen(modalId, html) {
    const modal = document.getElementById(modalId);

    modal.style.display = "block";
    
    modal.getElementsByClassName('modal-body')[0].innerHTML = html;

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
            modal.style.cursor = "pointer";
        }
    }    
}
//#endregion
