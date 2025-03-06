import {reactive} from "vue";

export const authorizationStore = reactive({
    isAuthorized: false,

    async updateAuthorizationData() {
        const res = await fetch(`/api/v1/authentication/authorized`);
        if(res.status !== 200) return;
        this.isAuthorized = await res.text() === "true";
    }
})