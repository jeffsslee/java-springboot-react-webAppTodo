import { Button, Grid, TextField } from "@mui/material";
import React, { useState } from "react";

const AddTodo = (props) => {
  const [item, setItem] = useState({ title: "" });
  const addItem = props.addItem;

  const handleAdd = () => {
    addItem(item);
    setItem({ title: "" });
  };

  const enterKeyEventHandler = (e) => {
    if (e.key === "Enter") {
      handleAdd();
    }
  };

  const onInputChange = (e) => {
    setItem({ title: e.target.value });
  };
  return (
    <Grid
      container
      sx={{
        marginTop: 20,
        marginBottom: 6,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
      }}
    >
      <Grid xs={9} md={9} item>
        <TextField
          required
          label="Add Todo"
          placeholder="To finish Material UI"
          fullWidth
          autoFocus
          value={item.title}
          onChange={onInputChange}
          onKeyDown={enterKeyEventHandler}
        />
      </Grid>
      <Grid xs={2} md={2} item>
        <Button
          variant="contained"
          fullWidth
          sx={{ height: "56px" }}
          onClick={handleAdd}
        >
          +
        </Button>
      </Grid>
    </Grid>
  );
};

export default AddTodo;
