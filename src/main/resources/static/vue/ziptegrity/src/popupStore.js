import {reactive} from "vue";

export const popupStore = reactive({
    isVisible: false,
    component: null,

    show(component) {
        popupStore.component = component;
        this.isVisible = true;
    },

    hide() {
        this.isVisible = false;
    }
})