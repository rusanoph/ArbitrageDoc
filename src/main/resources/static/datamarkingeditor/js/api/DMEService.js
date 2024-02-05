

export class DMEService {

    async createMarkedFileHandler() {
        return await showSaveFilePicker({
            suggestedName: 'name.marked',
            types: [{
                description: 'Marked file',
                accept: {'text/plain': ['.marked']},
            }],
        });
    }

    async createJsonFileHandler() {
        return await showSaveFilePicker({
            suggestedName: 'name.json',
            types: [{
                description: 'Json file',
                accept: {'application/json': ['.json']},
            }],
        });
    }

    async save(dataToSave, saveType) {

        let dataBlob;
        let writableStream;

        if (saveType === 'marked') {
            dataBlob = new Blob([dataToSave]);
            writableStream = await this.createMarkedFileHandler()
            .then(handler => handler.createWritable());

        } else if (saveType === 'json') {
            dataBlob = await this.postParseMarkedText(dataToSave)
            .then(parsedDataToSave => {
                const jsonDataToSave = JSON.stringify(parsedDataToSave);
                return new Blob([jsonDataToSave])
            });

            writableStream = await this.createJsonFileHandler()
            .then(handler => handler.createWritable());
        }

        await writableStream.write(dataBlob);
        await writableStream.close();
    }
    
    async postParseMarkedText(dataToSave) {
        let url = `/api/markdata/parse`;

        const response = await fetch(url, {
            method: 'POST',
            body: dataToSave,
        });
        
        let data = await response.json();

        return data;
    }
}
