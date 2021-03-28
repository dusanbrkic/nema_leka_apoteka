	Vue.component("Login", {
    data: function () {
        return {
            username: "",
            password: "",
            cookie: "",
            userRole : ""
        }
    },

    template: `
      <div id="login-div">
      <link rel="stylesheet" href="css/login.css" type="text/css">
      <h1 id="h1-login">Log in</h1>
      <form @submit.prevent="login">
        <table id="login-table">
          <tr>
            <td>Username:</td>
            <td><input type="text" v-model="username"></td>
          </tr>
          <tr>
            <td>Password:</td>
            <td><input type="password" v-model="password"></td>
          </tr>
          <tr>
            <td id="cancel-button">
              <input type="button" v-on:click="cancel" value="Cancel"></td>
            <td id="login-button">
              <input type="submit" v-on:submit="login" value="Log in"></td>
          </tr>
        </table>
      </form>
      </div>
    `
    ,
    methods: {
        cancel: function () {
            app.$router.push("/")
        },
        login: async function () {
            let user = {
                params: {
                    "username": this.username, "password": this.password
                }
            }
            await axios
                .get("korisnici/loginUser", user)
                .then(response => {
                    this.cookie = response.data.cookie
                    this.userRole = response.data.rola
                })
            localStorage.setItem("cookie", this.cookie)

            if (this.userRole === "PACIJENT"){
                app.$router.push("/home-pacijent")
            } else if (this.userRole === "DERMATOLOG"){
                app.$router.push("/home-dermatolog")
            } else if (this.userRole === "FARMACEUT"){
                app.$router.push("/home-farmaceut")
            } else if (this.userRole === "ADMIN_APOTEKE"){
                app.$router.push("/home-admin_apoteke")
            }
        }
    }
});