import {decodeFromBase64, decodeTextFromBase64, decryptValueBySharedSecret} from "@/cryptUtils.js";
import {CryptoManager} from "@/services/chat/cryptoManager.js";

export class ChatManager {
    static async getUserChats(onReadChat) {
        const res = await fetch("/api/v1/chats");
        if (res.status !== 200) return null;
        return Promise.all((await res.json()).map(async chat => {
            console.log("РАСШИФРОВКА ЧАТ-СООБЩЕНИЙ");
            console.log(chat.lastMessage);
            const secret = await onReadChat?.(chat.targetId);
            return ({
                username: chat.targetUsername,
                message: await CryptoManager.decryptTextBySharedSecret(chat.lastMessage, secret),
                date: chat.localDateTime,
                targetId: chat.targetId
            })
        }));
    }

    static async getChatMetadata(destinationId) {
        const res = await fetch(`/api/v1/chats/metadata?targetUserId=${destinationId}`);
        if (res.status !== 200) return null;
        const metadata = await res.json();
        return {
            targetUsername: metadata.targetUsername,
            targetId: metadata.targetId
        };
    }
}