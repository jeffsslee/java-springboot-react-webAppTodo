import {
  Avatar,
  Box,
  Button,
  Container,
  CssBaseline,
  Grid,
  Link as MaterialLink,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import { signup } from "../ApiService";

const Signup = () => {
  const [usernameError, setUsernameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    setUsernameError(false);
    setPasswordError(false);

    const data = new FormData(event.target);
    const username = data.get("username");
    const password = data.get("password");

    if (!username) setUsernameError(true);
    if (!password) setPasswordError(true);

    if (username && password) {
      signup({ username: username, password: password }).then((response) => {
        window.location.href = "/login";
      });
    }
  };
  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary.main" }}></Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="username"
            label="Email Address"
            name="username"
            autoComplete="email"
            autoFocus
            error={usernameError}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            error={passwordError}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign Up
          </Button>
          <Grid container>
            <Grid item xs>
              <MaterialLink component={RouterLink} to="/login" variant="body2">
                Already have an account? Go to login page!
              </MaterialLink>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
};

export default Signup;
