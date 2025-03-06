import {
    decryptValueByCodeWord,
    decryptValueBySharedSecret,
    encryptValueBySharedSecret,
    getDerivedSharedSecret
} from "@/cryptUtils.js";

export class CryptoManager {
    static async getPrivateKey() {
        const res = await fetch(`/api/v1/encryption/users/private`);
        if(res.status !== 200) return null;
        return await res.text();
    }

    static async getPublicKey(targetUserId) {
        const res = await fetch(`/api/v1/encryption/users/public/${targetUserId}`);
        if(res.status !== 200) return null;
        return await res.text();
    }

    static getCodeWord() {
        return localStorage.getItem("codeWord");
    }

    static setCodeWord(codeWord) {
        localStorage.setItem("codeWord", codeWord);
    }

    static async getDerivedSharedSecret(privateKey, publicKey, codeWord) {
        return await getDerivedSharedSecret(await decryptValueByCodeWord(privateKey, codeWord),
            publicKey);
    }

    static async encryptTextBySharedSecret(text, secret) {
        return await encryptValueBySharedSecret(text, secret);
    }

    static async decryptTextBySharedSecret(text, secret) {
        return await decryptValueBySharedSecret(text, secret);
    }
}