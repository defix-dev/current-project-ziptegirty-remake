import {CalculatorType} from "@/services/calculator/CalculatorType.js";

export class TokensLoader {
    static async load(type) {
        const f = await fetch(`/api/v1/calculator/${type}/tokens`);
        if(f.status !== 200) return null;
        return (await f.json()).map(v => ({
            type: v.type,
            keyword: v.keyword
        }));
    }
}