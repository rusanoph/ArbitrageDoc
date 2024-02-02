
export class Decorator {

    static withThisClosure(originalMethod) {
        return function (...args) {
            const self = this;
            return originalMethod.apply(self, args);
        }
    }

}

