<template>
    <form @submit.prevent="submit">
        <input v-model="data.username" placeholder="username" required>
        <input v-model="data.password" placeholder="password" required>
        <button type="submit">Login</button>
    </form>
    {{data}}
</template>

<script>
     import {reactive} from 'vue';

     export default {
        name: 'Login',
            setup() {
                const data = reactive ({
                    username: '',
                    password: ''
                });
                const submit = async () => {
                    await fetch ('/api/login', {
                        method: 'POST',
                        headers : {'Content-Type' : 'application/json'},
                        body: JSON.stringify(data)  
                    }).then(res => {
                        return res.json()
                    }).then (data => setUserData(data.user.username, data.token))  
                }
                return {
                    data,
                    submit
                }
            }
        } 
        function setUserData (username, token) {
            sessionStorage.setItem('token', 'Bearer ' + token)
            sessionStorage.setItem('username',  username)
        }
</script>


