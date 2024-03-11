import { Box, Button, Typography } from "@mui/material";
import { grey } from "@mui/material/colors";
import React from "react";
import { signout } from "../ApiService";

const NavBar = () => {
  return (
    <Box
      sx={{
        backgroundColor: "primary.main",
        color: grey[50],
        width: "100%",
        padding: "16px",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "relative",
      }}
    >
      <Typography variant="h4" component="div">
        Todo List
      </Typography>
      <Button
        color="inherit"
        onClick={signout}
        sx={{
          position: "absolute",
          right: "48px",
        }}
      >
        Logout
      </Button>
    </Box>
  );
};

export default NavBar;
