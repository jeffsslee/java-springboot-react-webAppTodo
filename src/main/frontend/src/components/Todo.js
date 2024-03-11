import {
  Checkbox,
  FormControlLabel,
  FormGroup,
  Grid,
  IconButton,
  InputBase,
  ListItem,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import React, { useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import { deepOrange, grey } from "@mui/material/colors";

const Todo = (props) => {
  const [item, setItem] = useState(props.item);
  const [readOnly, setReadOnly] = useState(true);
  const deleteItem = props.deleteItem;
  const editItem = props.editItem;

  const editEventHandler = (e) => {
    // item.title = e.target.value;
    // editItem(item);
    setItem({ ...item, title: e.target.value });
  };

  const checkBoxEventHandler = (e) => {
    item.done = e.target.checked;
    editItem(item);
  };

  const turnOffReadOnly = () => {
    setReadOnly(false);
  };

  const turnOnReadOnly = (e) => {
    if (e.key === "Enter") {
      setReadOnly(true);
      editItem(item);
    }
  };

  const deleteEventHandler = () => {
    deleteItem(item);
  };
  return (
    <ListItem sx={{ border: `1px solid ${grey[300]}`, margin: "8px 0" }}>
      <Checkbox checked={item.done} onChange={checkBoxEventHandler} />
      <ListItemText>
        <InputBase
          // sx={{ border: "1px solid red" }}
          sx={{
            padding: "8px",
            bgcolor: !readOnly ? deepOrange[100] : "inherit",
          }}
          type="text"
          id={item.id}
          name={item.id}
          value={item.title}
          multiline={true}
          fullWidth={true}
          readOnly={readOnly}
          onClick={turnOffReadOnly}
          onKeyDown={turnOnReadOnly}
          onChange={editEventHandler}
        />
      </ListItemText>
      <ListItemIcon sx={{ justifyContent: "center" }}>
        <DeleteIcon
          onClick={deleteEventHandler}
          sx={{ "&:hover": { cursor: "pointer" } }}
        ></DeleteIcon>
      </ListItemIcon>
    </ListItem>
  );
};

export default Todo;
