
export class DocumentService {
    
    static async getDocumentText(path, filename, formated=false) {
        let params = {
            "documentPath": path,
            "documentFileName": filename,
            "formated": formated,
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/text?${query}`;
    
        const response = await fetch(url);
        let data = await response.json();

        return data;
    } 

    static async getDocumentWordStatistic(text) {
        let params = {
            "text": text,
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/text/statistic?${query}`;
    
        const response = await fetch(url, {
            method: 'POST',
            body: text,
        });
        let data = await response.json();

        return data;
    }

    static async getListOfFiles(path=null) {
        if (path === null) {
            path = "";
        }

        let params = {
            "directoryPath": path,
        }

        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");

        let url = `/api/document/list/doc?${query}`;

        const response = await fetch(url);
        let data = await response.json();

        return data;
    }

    static async getListOfDirectories(path=null) {

        if (path === null) {
            path = "";
        }

        let params = {
            "directoryPath": path,
        }

        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");

        let url = `/api/document/list/dir?${query}`;

        const response = await fetch(url);
        let data = await response.json();

        return data;
    }
}