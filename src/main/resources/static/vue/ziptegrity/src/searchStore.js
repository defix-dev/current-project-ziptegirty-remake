import {reactive} from "vue";

export const searchStore = reactive({
   searchText: "",

   getSearchText() {
       return this.searchText;
   }
});