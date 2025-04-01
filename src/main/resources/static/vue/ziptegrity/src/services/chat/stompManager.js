import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {encodeTextToBase64, encodeToBase64} from "@/cryptUtils.js";
import {CryptoManager} from "@/services/chat/cryptoManager.js";

export class StompManager {
    initialize(endpoint=`${window.location.protocol === "https:" ? "https:" : "http:"}//${window.location.host}/ws`) {
        this.client = Stomp.over(new SockJS(endpoint));
    }

    subscribeChatListen(currentUserId, messageAdapter) {
        const subscribeAction = () => {
            this.client?.subscribe(`/chat/listen/${currentUserId}`, (messageBodyHolder) => {
                messageAdapter?.(JSON.parse(messageBodyHolder.body));
            });
        }
        try {
            subscribeAction();
        } catch (_) {
            this.client.connect({}, (_) => subscribeAction());
        }
    }

    async sendMessage(destinationId, message, secret, onError) {
        if (this.client == null || message === "") {
            onError?.();
            return;
        }
        this.client.send(
            `/services/chat/${destinationId}`,
            {},
            await CryptoManager.encryptTextBySharedSecret(message, secret)
        );
    }
}