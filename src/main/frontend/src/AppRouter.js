import { Box, Typography } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";
import Login from "./pages/Login";
import Signup from "./pages/Signup";

const Copyright = () => {
  return (
    <Box>
      <Typography variant="body2" color={"textSecondary"} align="center">
        {"Copyright"} &#169; Jeff Note, {new Date().getFullYear()}
        {"."}
      </Typography>
    </Box>
  );
};

function AppRouter() {
  return (
    <Box>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
        </Routes>
      </BrowserRouter>
      <Box mt={5}>
        <Copyright />
      </Box>
    </Box>
  );
}

export default AppRouter;
