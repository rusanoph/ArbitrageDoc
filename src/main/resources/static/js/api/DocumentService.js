
export class DocumentService {
    
    static async getDocumentText(path, filename, formated=false, lemma=false) {
        let params = {
            "documentPath": path,
            "documentFileName": filename,
            "formated": formated,
            "lemma": lemma,
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/text?${query}`;
    
        const response = await fetch(url);
        let data = await response.json();

        return data;
    } 

    static async postDocumentWordStatistic(text) {
        let url = `/api/document/text/statistic`;
    
        const response = await fetch(url, {
            method: 'POST',
            body: text,
        });
        let data = await response.json();

        return data;
    }

    static async postDocumentTextLemmaValid(text) {
        let url = `/api/document/text/lemma/valid`;
    
        const response = await fetch(url, {
            method: 'POST',
            body: text,
        });
        let data = await response.json();

        return data;
    }


    static async postDocumentTextLemmaInvalid(text) {
        let url = `/api/document/text/lemma/invalid`;
    
        const response = await fetch(url, {
            method: 'POST',
            body: text,
        });
        let data = await response.json();

        return data;
    }

    static async getTitleDataTable(path=null) {
        if (path === null) {
            path = "";
        }

        let params = {
            "directoryPath": path,
        }

        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");

        let url = `/api/document/list/title?${query}`;

        const response = await fetch(url);
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