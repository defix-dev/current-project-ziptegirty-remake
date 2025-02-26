import {decodeFromBase64, decodeTextFromBase64} from "@/cryptUtils.js";
import {CryptoManager} from "@/services/chat/cryptoManager.js";

export class MessageManager {
    static async getMessages(currentUserId, destinationId, secret) {
        const res = await fetch(`/api/v1/messages?targetUserId=${destinationId}`);
        if (res.status !== 200) return null;
        return Promise.all((await res.json()).map(async message => {
            console.log("РАСШИФРОВКА ПРОСТО СООБЩЕНИЙ");
            console.log(message.message);
            return ({
                message: await CryptoManager.decryptTextBySharedSecret(message.message, secret),
                date: message.createdAt,
                isUser: (message.senderId === currentUserId)
            });
        }));
    }
}