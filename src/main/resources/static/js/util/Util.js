export class Util {
    
    static saveValue(e) {
        let id = e.id;
        let value = e.value;
        localStorage.setItem(id, value);
    }
    
    static getSavedValue(v) {
        if (!localStorage.getItem(v)) {
            return "";
        }
        return localStorage.getItem(v);
    }

    static removeSavedValue(v) {
        if (localStorage.getItem(v)) {
            localStorage.removeItem(v);
        }
    }
}