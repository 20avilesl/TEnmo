<template>
    <form @submit.prevent="submit">
        <input v-model="data.username" placeholder="username" required>
        <input v-model="data.password" placeholder="password" required>
        <button type="submit">Register</button>
    </form>
    {{data}}
</template>

<script>
     import {reactive} from 'vue';
     import {useRouter} from 'vue-router'

     export default {
        name: 'Register',
            setup() {
                const data = reactive ({
                    username: '',
                    password: ''
                });
                const router = useRouter();
                const submit = async () => {
                    await fetch ('/api/register', {
                        method: 'POST',
                        headers : {'Content-Type' : 'application/json',},
                        body: JSON.stringify(data)  
                    })
                }
                return {
                    data,
                    submit
                }
            }
        } 
</script>


