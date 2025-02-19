import {reactive} from "vue";

export const tooltipStore = reactive({
    isVisible: false,
    component: null,
    props: null,
    targetElement: null,

    show(component, targetElement, props=null) {
        if(this.isVisible) this.hide();

        tooltipStore.component = component;
        tooltipStore.props = props;
        tooltipStore.targetElement = targetElement;

        this.isVisible = true;
    },

    hide() {
        this.isVisible = false;
    }
})