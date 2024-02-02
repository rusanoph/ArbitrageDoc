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

    static isNumeric(str) {
        if (typeof str !== "string")
            return false;
        return !isNaN(str) && !isNaN(parseFloat(str));
    }

    static onEnterPressedEvent(action) {
        if (event.key === "Enter") {
            action();
        }
    }

    static onArrowRightPressedEvent(action) {
        if (event.code === "ArrowRight") {
            action();
        }
    }

    static onArrowLeftPressedEvent(action) {
        if (event.code === "ArrowLeft") {
            action();
        }
    }

    static findParent(element, selector) {
        if (!element) return null;

        return element.matches && element.matches(selector) ? element : this.findParent(element.parentNode, selector);
    }

    static wrapText(text, wrapNumber) {
        if (text === undefined || text === null || text) {
            return "";
        } 

        const regex = new RegExp(`.{${wrapNumber}}`, "g");

        const testSplit = text.match(regex) === null ? [text] : text.match(regex);

        return testSplit.join("\r\n");
    }
}