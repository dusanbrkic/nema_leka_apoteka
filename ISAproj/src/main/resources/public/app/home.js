Vue.component("Home", {
  data: function () {
    return {};
  },
  mounted() {
  	message: ''
  	apoteka: ''
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
          <label for="id">ID Apoteke:</label>
          <input v-model="message"><br><br>
          <input type="submit" value="Submit" v-on:click="redirectToApoteka(message)">
        </div>
    `,
  methods: {
    redirectToHelloWorld: function () {
      app.$router.push("/helloWorld");
    },
    redirectToApoteke: function () {
      app.$router.push("/apoteke");
    },
    redirectToApoteka: function (id) {
      app.$router.push("/apoteka/" + id);
    },
  },
});
