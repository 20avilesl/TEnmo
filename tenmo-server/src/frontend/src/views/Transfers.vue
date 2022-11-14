<template>
    <form @submit.prevent="getAllTransfers">
        <button type="submit">Get All Transfers</button>
    </form>
      <form @submit.prevent="getTransferById">
        <input v-model="data.transactionId" placeholder="enter the transaction id" required>
        <button type="submit">Get Transfer</button>
    </form>
    
</template>

<script>
     import {reactive} from 'vue';

     export default {
        name: 'Transfers',
            setup() {
                const data = reactive ({
                    transactionId: 0
                });
                const getAllTransfers = async () => {
                    await fetch ('/api/transfers/', {
                        method: 'GET',
                        headers : {
                            'Content-Type' : 'application/json',
                            'Authorization': sessionStorage.getItem('token')
                            },
                    }).then(res => {
                        return res.json()
                    }).then (data => console.table(data))
                   
                }
                 const getTransferById = async () => {
                    await fetch ('/api/transfers/3001' , {
                        method: 'GET',
                        headers : {
                            'Content-Type' : 'application/json',
                            'Authorization': sessionStorage.getItem('token')
                            },
                    }).then(res => {
                        return res.json()
                    }).then (data => console.table(data))
                   
                }
                return {
                    data,
                    getAllTransfers,
                    getTransferById
                }
            }
        } 
</script>
