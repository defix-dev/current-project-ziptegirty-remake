import {encodeTextToBase64} from "@/cryptUtils.js";

export class ExpressionCalculator {
    static async calculate(expression, type) {
        const f = await fetch(`/api/v1/calculator/${type}`, {
            method: "POST",
            body: JSON.stringify({"expression":encodeTextToBase64(expression)}),
            headers: { "Content-Type": "application/json" }
        });
        if(f.status !== 200) return null;
        return Number.parseFloat(await f.text());
    }
}