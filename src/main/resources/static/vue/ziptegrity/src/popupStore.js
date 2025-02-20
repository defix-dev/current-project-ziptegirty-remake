import {reactive} from "vue";

export const popupStore = reactive({
    isVisible: false,
    component: null,
    props: null,

    show(component, props=null) {
        popupStore.component = component;
        popupStore.props = props;
        this.isVisible = true;
    },

    hide() {
        this.isVisible = false;
    }
})