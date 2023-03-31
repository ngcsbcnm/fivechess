<template>


	<section class="h-100">
		<div class="container h-100">
			<div class="row justify-content-sm-center h-100">
				<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">

					<div class="card shadow-lg">
						<div class="card-body p-5">
							<h1 class="fs-4 card-title fw-bold mb-4">Login</h1>
							<form @submit.prevent="login">
								<div class="mb-3">
									<label class="mb-2 text-muted" for="username">用户名</label>
									<input v-model="username" id="username" type="username" class="form-control"
										placeholder="请输入用户名">

								</div>

								<div class="mb-3">
									<div class="mb-2 w-100">
										<label class="text-muted" for="password">密码</label>

									</div>
									<input v-model="password" id="password" type="password" class="form-control"
										placeholder="请输入密码">

								</div>

								<div class="error-message">
									{{ error_message }}
								</div>

								<div class="d-grid gap-2">
									<button class="btn btn-primary" type="submit">登录</button>

								</div>



							</form>
						</div>
						<div class="card-footer py-3 border-0">
							<div class="text-center">
								Don't have an account? <router-link :to="{ name: 'register' }" class="text-dark">Create
									One</router-link>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>





</template>



<script>
//import ContentField from '@/components/ContentField'



import { ref } from 'vue';

import { useStore } from 'vuex';

import router from '@/router/index';

export default {
	name: 'LoginIndexView',
	components: {
		//ContentField,
	},
	setup() {

		let username = ref("");
		let password = ref("");
		let error_message = ref("");
		const store = useStore();
		const jwt_token = localStorage.getItem("jwt_token");

		if (jwt_token) {
			store.commit("updateToken", jwt_token);
			store.dispatch("getinfo", {
				success() {
					router.push({ name: 'pk-index' });
				},
				error() {
					console.log("error");
				}
			})
			//console.log(jwt_token);
		}

		const login = () => {
			error_message.value = "";
			store.dispatch("login", {
				username: username.value,
				password: password.value,
				success() {
					store.dispatch("getinfo", {
						success() {
							router.push({ name: 'pk-index' });
						},
						error() {
							//resp = '';
							console.log(false);
						}
					})

				},
				error() {
					error_message.value = "用户名或密码错误";

				}
			})

		}
		return {
			password,
			username,
			error_message,
			login
		}
	}
}

</script>

<style scoped>
.h-100 {
	margin-top: 80px;
}

.error-message {
	color: red;
}
</style>

