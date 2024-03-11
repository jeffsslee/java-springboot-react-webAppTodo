export function call(url, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken !== null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }
  let options = {
    headers: headers,
    method: method,
  };

  if (request) {
    // To add body(property), and change JSON to stringify
    options.body = JSON.stringify(request);
  }
  return fetch(url, options)
    .then((res) => {
      if (res.status === 200) {
        return res.json();
      } else if (res.status === 400) {
        return res;
      } else if (res.status === 403) {
        window.location.href = "/login";
      } else {
        Promise.reject(res);
        throw Error(res);
      }
    })
    .catch((err) => {
      console.log("http error!");
      console.log(err);
    });
}

export function signin(userDTO) {
  return call(`/api/auth/signin`, "POST", userDTO).then((res) => {
    if (res.token) {
      console.log(`res.token : ` + res.token);
      localStorage.setItem("ACCESS_TOKEN", res.token);
      window.location.href = "/";
    } else if (res.status === 400) {
      alert("Check your credential and try again!");
      window.location.href = "/login";
    }
  });
}

export function signout() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href = "/login";
}

export function signup(userDTO) {
  return call(`/api/auth/signup`, "POST", userDTO);
}
