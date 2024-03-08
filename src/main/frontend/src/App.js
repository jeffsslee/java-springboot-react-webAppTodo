import { useEffect, useState } from "react";
import "./App.css";
import { Box, Grid, List, Paper, styled } from "@mui/material";
import Todo from "./components/Todo";
import AddTodo from "./components/AddTodo";
import NavBar from "./components/NavBar";
import { call } from "./ApiService";

const StyledBox = styled(Box)({
  // border: "1px solid red",
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
});
const StyledDisplayBox = styled(Box)({
  // border: "1px solid blue",
  width: "100%",
  maxWidth: "1200px",
});
const StyledDisplayInnerBox = styled(Box)({
  // border: "1px solid yellow",
  margin: "24px",
});

function App() {
  const [items, setItems] = useState([
    // {
    //   id: "0",
    //   title: "React",
    //   done: true,
    // },
    // {
    //   id: "1",
    //   title: "Spring boot",
    //   done: false,
    // },
    // {
    //   id: "2",
    //   title: "Javascript",
    //   done: false,
    // },
  ]);

  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  };

  useEffect(() => {
    // fetch(`/api/todo`, requestOptions)
    //   .then((res) => res.json())
    //   .then((res) => setItems(res.data));
    call("/api/todo", "GET", null).then((res) => setItems(res.data));
  }, []);

  const addItem = (item) => {
    // item.id = "ID-" + items?.length;
    // item.done = false;
    // setItems([...items, item]);
    call("/api/todo", "POST", item).then((res) => setItems(res.data));
  };

  const deleteItem = (item) => {
    // const remainItems = items.filter((e) => e.id !== item.id);
    // setItems([...remainItems]);
    call("/api/todo", "DELETE", item).then((res) => setItems(res.data));
  };

  const editItem = (item) => {
    // setItems([...items]);
    // console.log(items);
    call("/api/todo", "PUT", item).then((res) => setItems(res.data));
  };

  let todoItems = items?.length > 0 && (
    <Paper sx={{ padding: "8px" }}>
      <List>
        {items?.map((item) => (
          <Todo
            item={item}
            key={item.id}
            deleteItem={deleteItem}
            editItem={editItem}
          />
        ))}
      </List>
    </Paper>
  );

  let todoListPage = (
    <StyledDisplayBox>
      <StyledDisplayInnerBox>
        <AddTodo addItem={addItem} />
        {todoItems}
      </StyledDisplayInnerBox>
    </StyledDisplayBox>
  );

  let content = todoListPage;

  return (
    <StyledBox>
      <NavBar />
      {content}
    </StyledBox>
  );
}

export default App;
