export function encodeToBase64(value) {
    return btoa(String.fromCharCode(...new Uint8Array(value)));
}

export function decodeFromBase64(value) {
    return new Uint8Array(atob(value).split("").map(c => c.charCodeAt(0)));
}

export function encodeTextToBase64(text) {
    return btoa(String.fromCharCode(...new TextEncoder().encode(text)));
}

export function decodeTextFromBase64(text) {
    return new TextDecoder().decode(Uint8Array.from(atob(text), c => c.charCodeAt(0)));
}

export async function generateECDHKeyPair() {
    const keyPair = await window.crypto.subtle.generateKey(
        {
            name: "ECDH",
            namedCurve: "P-256"
        },
        true,
        ["deriveKey"]
    );
    return {
        privateKey: encodeToBase64(await crypto.subtle.exportKey("pkcs8", keyPair.privateKey)),
        publicKey: encodeToBase64(await crypto.subtle.exportKey("spki", keyPair.publicKey))
    };
}

const STATIC_IV = new Uint8Array(16);
const STATIC_SALT = new Uint8Array(16);

async function createCodeWordDerivedKey(codeWord) {
    const keyMaterial = await crypto.subtle.importKey(
        "raw", new TextEncoder().encode(codeWord), {name: "PBKDF2"}, false, ["deriveKey"]
    );
    return await window.crypto.subtle.deriveKey(
        {
            name: "PBKDF2",
            salt: STATIC_SALT,
            iterations: 100000,
            hash: "SHA-256"
        },
        keyMaterial,
        {name: "AES-GCM", length: 256},
        false,
        ["encrypt", "decrypt"]
    );
}

export async function encryptValueByCodeWord(codeWord, value) {
    const key = await createCodeWordDerivedKey(codeWord);
    const encryptedData = await window.crypto.subtle.encrypt(
        {
            name: "AES-GCM",
            iv: STATIC_IV
        },
        key,
        new TextEncoder().encode(value)
    );
    return encodeToBase64(encryptedData);
}

export async function decryptValueByCodeWord(value, codeWord) {
    const key = await createCodeWordDerivedKey(codeWord);
    const decryptedData = await window.crypto.subtle.decrypt(
        {
            name: "AES-GCM",
            iv: STATIC_IV
        },
        key,
        decodeFromBase64(value)
    );
    return new TextDecoder().decode(decryptedData);
}

export async function getDerivedSharedSecret(privateKey, publicKey) {
    const imPrivateKey = await window.crypto.subtle.importKey(
        "pkcs8",
        decodeFromBase64(privateKey).buffer,
        { name: "ECDH", namedCurve: "P-256" },
        false,
        ["deriveKey"]
    );

    const imPublicKey = await window.crypto.subtle.importKey(
        "spki",
        decodeFromBase64(publicKey).buffer,
        { name: "ECDH", namedCurve: "P-256" },
        false,
        []
    );

    return await window.crypto.subtle.deriveKey(
        {
            name: "ECDH",
            public: imPublicKey
        },
        imPrivateKey,
        { name: "AES-GCM", length: 256 },
        true,
        ["encrypt", "decrypt"]
    );
}

export async function encryptValueBySharedSecret(value, secret) {
    const encryptedData = await window.crypto.subtle.encrypt(
        {
            name: "AES-GCM",
            iv: STATIC_IV
        },
        secret,
        new TextEncoder().encode(value)
    );
    return encodeToBase64(encryptedData);
}

export async function decryptValueBySharedSecret(value, secret) {
    const decryptedData = await window.crypto.subtle.decrypt(
        {
            name: "AES-GCM",
            iv: STATIC_IV
        },
        secret,
        decodeFromBase64(value)
    );
    return new TextDecoder().decode(decryptedData);
}