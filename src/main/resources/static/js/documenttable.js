import { DocumentService } from "./api/DocumentService.js";
import { Util } from "./util/Util.js";

class DataTablePage {

    pageSize = 10;
    currentPage = 1;
    maxPage;

    constructor(elementId, preloaderId) {
        this.generateDataTable(elementId, preloaderId, this.currentPage);
    }

    async generateDataTable(elementId, preloaderId, currentPage) {        
        let dataTableBodyElement = document.getElementById(elementId);

        // Set preloader
        const preloaderElement = document.createElement("div");
        preloaderElement.classList.add("loader");
        document.getElementById(preloaderId).innerHTML = "";
        document.getElementById(preloaderId).appendChild(preloaderElement);

        // For pagination
        const startIndex = (currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        let maxRowHeight = "200px";
        let orderIndex = startIndex + 1;

        // Remove Table Content
        dataTableBodyElement.innerHTML = "";

        let allDataDirs = await DocumentService.getDeepListOfDirectories()
        .then( async (dirList) =>  {
            dirList = [...dirList, ""];

            let finalFileList = []
            for (let dir of dirList) {
                let currentFileList = await DocumentService.getListOfFiles(dir);
                currentFileList = currentFileList.map(file => ({
                    dir: dir,
                    file: file,
                }));
                finalFileList = [...finalFileList, ...currentFileList];
            }

            this.maxPage = Math.ceil(finalFileList.length / this.pageSize);
            
            // Get data to current page
            finalFileList = finalFileList.slice(startIndex, endIndex);

            // Generate Data Table
            for (let file of finalFileList) {
                let tr = document.createElement("tr");
            
                let tdOrder = document.createElement("td");
                let tdFilename = document.createElement("td");
                let tdCourt = document.createElement("td");

                tdOrder.innerHTML = orderIndex;
                tdFilename.innerHTML = `${file.dir}\\${file.file}`;

                let court = await DocumentService.getDocumentCourt(file.dir, file.file);
                if (court.includes("Суд не определен")) {
                    court = `<span class='highlight'>${court}</span>`
                }
                tdCourt.innerHTML = `<div style="overflow-y: auto; max-height: ${maxRowHeight}">${court}</div>`;

                tr.appendChild(tdOrder);
                tr.appendChild(tdFilename);
                tr.appendChild(tdCourt);

                dataTableBodyElement.appendChild(tr);

                orderIndex++;
            }

            // Change current table page
            this.currentPage = currentPage;
            for (let element of document.getElementsByClassName("current-page")) {
                element.innerHTML = `${currentPage} / ${this.maxPage}`
            }

            // Remove Preloader
            preloaderElement.remove();
        });
        
    }

    static async addDataTablePagination(elementPagination, dataTable, goToPage) {
        elementPagination.addEventListener('click', async () => {
            if (!(1 <= dataTable.currentPage + goToPage && dataTable.currentPage + goToPage <= dataTable.maxPage)) {
                return;
            }
        
            await dataTable.generateDataTable("data-table-body", "data-table-preloader", dataTable.currentPage + goToPage);
        });
    }
}




const page = new DataTablePage("data-table-body", "data-table-preloader");

//#region Table update
const reloadTableButton = document.getElementById("reload-table-button");
reloadTableButton.addEventListener('click', async () => {
    await page.generateDataTable("data-table-body", "data-table-preloader", page.currentPage);
});
//#endregion

//#region Pagination
const nextPageButton = document.getElementById("next-page-button");
DataTablePage.addDataTablePagination(nextPageButton, page, 1);
window.addEventListener('keydown', () => 
    Util.onArrowRightPressedEvent(() => {
        nextPageButton.dispatchEvent(new Event('click')); 
    })
);

const previousPageButton = document.getElementById("previous-page-button");
DataTablePage.addDataTablePagination(previousPageButton, page, -1);

window.addEventListener('keydown', () => 
    Util.onArrowLeftPressedEvent(() => { 
        previousPageButton.dispatchEvent(new Event('click')); 
    })
);
//#endregion

//#region Go to page input
const goToPageInput = document.getElementById("go-to-page-input");
const goToPageButton = document.getElementById("go-to-page-button");
goToPageButton.addEventListener('click', async () => {

    let tablePage = goToPageInput.value;
    
    if (Util.isNumeric(tablePage)) {
        if (!(1 <= tablePage && tablePage <= page.maxPage)) {
            return;
        }

        await page.generateDataTable("data-table-body", "data-table-preloader", parseInt(tablePage));
    }
});

goToPageInput.addEventListener('keydown', () => 
    Util.onEnterPressedEvent(() => { 
        goToPageButton.dispatchEvent(new Event('click')); 
    })
);
//#endregion


