

export class DMEService {
    
    async postMarkedTextToParse(dataToSave, saveType){
        if (saveType === null) {
            saveType = "";
        }

        let params = {
            "saveType": saveType,
        }

        let query = Object.keys(params)
            .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
            .join("&");

        let url = `/api/markdata/parse?${query}`;

        const response = await fetch(url, {
            method: 'POST',
            body: dataToSave,
        });
        
        let data = await response.json();

        return data;
    }

    async saveFileDialog() {
        // ...
    }

}
