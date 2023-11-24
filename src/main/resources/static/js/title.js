import { DocumentService } from "./api/DocumentService.js";
import { Util } from "./util/Util.js";

class TitlePage {

    constructor(elementId, preloaderId) {
        this.generateTitleTable(elementId, preloaderId);
    }

    async generateTitleTable(elementId, preloaderId, currDir=null) {
        if (currDir === null) {
            currDir = "";
        }
        
        let titleTableBodyElement = document.getElementById(elementId);
        if (localStorage.getItem(elementId) !== null) {
            titleTableBodyElement.innerHTML = localStorage.getItem(elementId);
            return;
        } 

        // Set preloader
        const preloaderElement = document.createElement("div");
        preloaderElement.classList.add("loader");
        document.getElementById(preloaderId).innerHTML = "";
        document.getElementById(preloaderId).appendChild(preloaderElement);

        // Remove Table Content
        titleTableBodyElement.innerHTML = "";

        await DocumentService.getTitleDataTable(currDir)
        .then(titleList => {
            const maxRowHeight = "200px";

            // Append rows
            for (let i = 0; i < titleList.length; i++) {
                let tr = document.createElement("tr");

                let tdOrder = document.createElement("td");
                let tdTitle = document.createElement("td");
                let tdCount = document.createElement("td");
                let tdFiles = document.createElement("td");

                tdOrder.innerHTML = i + 1;
                tdTitle.innerHTML = `<div style="overflow-y: auto; max-height: ${maxRowHeight}">${titleList[i].title}</div>`;
                tdCount.innerHTML = titleList[i].count;
                tdFiles.innerHTML = `<div style="overflow-y: auto; max-height: ${maxRowHeight}">${titleList[i].files}</div>`;

                tr.appendChild(tdOrder);
                tr.appendChild(tdTitle);
                tr.appendChild(tdCount);
                tr.appendChild(tdFiles);

                titleTableBodyElement.appendChild(tr);
            }

            // Save table result
            localStorage.setItem(elementId, titleTableBodyElement.innerHTML);

            // Remove Preloader
            preloaderElement.remove();
        });

        
    }

}

const page = new TitlePage("title-table-body", "title-table-preloader");

const reloadTableButton = document.getElementById("reload-table-button");
reloadTableButton.addEventListener('click', async () => {
    localStorage.removeItem("title-table-body");

    await page.generateTitleTable("title-table-body", "title-table-preloader");
});