Vue.component("Home", {
    data: function () {
        return {}
    },
    mounted() {

    },
    template: `
        <div>
          <p>Dobrodosli u najjaci sistem apoteka na Balkanu a i sire!</p>
          <table>
            <tr>
              <td>Say</td>
              <td>
                <button v-on:click="redirectToHelloWorld">Hello World!</button>
              </td>
              <td>
              	<button v-on:click="redirectToApoteke">Apoteke</button>
              </td>
            </tr>
          </table>
        </div>
    `
    ,
    methods: {
        redirectToHelloWorld: function () {
            app.$router.push("/helloWorld")
        },
        redirectToApoteke: function (){
        	app.$router.push("/apoteka")
        }
    }
});