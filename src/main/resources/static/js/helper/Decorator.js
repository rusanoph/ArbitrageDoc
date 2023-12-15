
// Not Implemented Class
export class Decorator {

    static loadableElement(htmlElementGentFunction, preloaderId) {


        return function () {
            
            htmlElementGentFunction();
        };
    }

}