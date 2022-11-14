<template>
    <form @submit.prevent="submit">
        <button type="submit">Get Balance</button>
    </form>
    {{balance}}
</template>

<script>
     import {reactive} from 'vue';

     export default {
        name: 'Account',
            setup() {
                const data = reactive ({
                    balance: ''
                });
                const submit = async () => {
                    await fetch ('/api/accounts', {
                        method: 'GET',
                        headers : {'Content-Type' : 'application/json', 
                        'Authorization': sessionStorage.getItem('token')},
                    }).then(res => {
                        return res.json()
                    }).then (data => console.log('balance: ' + data))
                    }
                return {
                    data,
                    submit
                }
            }
        }
</script>


