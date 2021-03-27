Vue.component("HomeDermatolog", {
    data: function () {
        return {
            cookie : "",

        };
    },
    mounted() {
        this.cookie = localStorage.getItem("cookie")
    },
    template: `
        <div>
          <p>Home za dermatologa, tvoj izgenerisani token je {{cookie}}</p>
        </div>
    `,
    methods: {

    },
});
