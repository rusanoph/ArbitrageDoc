
async function GetDocumentText(documentName) {
    const response = await fetch(`/api/document/${documentName}`);
    let obj = await response.json();
    
    return obj;
}


let apiDataParagraph = document.getElementById("api-data"); 
// apiDataParagraph.innerHTML = ;


GetDocumentText("Data").then(json => {
    apiDataParagraph.innerHTML = json.documentPath;
});