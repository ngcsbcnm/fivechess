<template>


	<section class="h-100">
		<div class="container h-100">
			<div class="row justify-content-sm-center h-100">
				<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">

					<div class="card shadow-lg">
						<div class="card-body p-5">
							<h1 class="fs-4 card-title fw-bold mb-4">Register</h1>
							<form @submit.prevent="register">
								<div class="mb-3">
									<label class="mb-2 text-muted" for="name">用户名</label>
									<input v-model="username" id="name" type="text" class="form-control">
									<div class="invalid-feedback">
										Name is required
									</div>
								</div>

								<div class="mb-3">
									<label class="mb-2 text-muted" for="email">密码</label>
									<input v-model="password" id="password" type="password" class="form-control">
									<div class="invalid-feedback">
										Email is invalid
									</div>
								</div>

								<div class="mb-3">
									<label class="mb-2 text-muted" for="password">确认密码</label>
									<input v-model="confirmPassword" id="confirmpassword" type="password"
										class="form-control">
									<div class="invalid-feedback">
										Password is required
									</div>
								</div>


								<div class="error-message">{{ error_message }}</div>

								<div class="d-grid gap-2">
									<button class="btn btn-primary" type="submit">注册</button>

								</div>
							</form>
						</div>
						<div class="card-footer py-3 border-0">
							<div class="text-center">
								Already have an account? <router-link :to="{ name: 'login' }"
									class="text-dark">Login</router-link>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>





</template>



<script>

import $ from 'jquery'
import { ref } from 'vue'
import router from '@/router/index'
export default {
	name: 'RegisterIndexView',
	components: {
	},
	setup() {

		let username = ref("");
		let password = ref("");
		let confirmPassword = ref("");
		let error_message = ref("");
		const register = () => {
			//error_message.value = "";
			$.ajax({
				url: "https://app4450.acapp.acwing.com.cn/api/user/register/",
				type: 'post',
				data: {
					username: username.value,
					password: password.value,
					confirmPassword: confirmPassword.value
				},
				success(resp) {
					if (resp.msg === "success") {
						router.push({ name: 'login' })
					} else {
						error_message.value = resp.msg;
					}

				},
				error(resp) {

					console.log(resp)
				}


			})

		}
		return {
			register,
			username,
			password,
			confirmPassword,
			error_message
		}

	}
}

</script>

<style scoped>
.h-100 {
	margin-top: 60px;
}

.error-message {
	color: red;
}
</style>