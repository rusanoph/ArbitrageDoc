
export class DocumentService {

    static async postTextToGetEntities(text) {
        const url = "/api/markdata/entities";

        const response = await fetch(url, {
            method: 'POST',
            body: text,
        });
        let data = await response.json();

        return data;
    }
    
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

    static async getDocumentSentencies(path, filename) {
        let params = {
            "documentPath": path,
            "documentFileName": filename
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/sentencies?${query}`;
    
        const response = await fetch(url);
        let data = await response.json();

        return data;
    }

    static async getDocumentStructureParts(path, filename) {
        let params = {
            "documentPath": path,
            "documentFileName": filename,
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/text/part?${query}`;
    
        const response = await fetch(url);
        let data = await response.json();

        return data;
    }

    static async getDocumentCourt(path, filename) {
        let params = {
            "documentPath": path,
            "documentFileName": filename,
        }
    
        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");
    
        let url = `/api/document/court?${query}`;
    
        const response = await fetch(url);
        let data = await response.text();

        return data;
    }

    static async postDocumentMoneySumHyphen(text) {
        let url = `/api/document/text/data/moneysum/hyphen`;

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

    static async getDeepListOfDirectories(path=null) {
        if (path === null) {
            path = "";
        }

        let params = {
            "directoryPath": path,
        }

        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");

        let url = `/api/document/list/dir/deep?${query}`;

        const response = await fetch(url);
        let data = await response.json();

        return data;
    }
}