export function call(url, method, request) {
  let options = {
    headers: new Headers({
      "Content-Type": "application/json",
    }),
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
      }
    })
    .catch((err) => {
      console.log("http error!");
      console.log(err);
    });
}
