const Greet = ({ name }: { name?: string }) => {
  if (name) return <h1>hi {name}</h1>;
  return <button>login</button>;
};

export default Greet;
