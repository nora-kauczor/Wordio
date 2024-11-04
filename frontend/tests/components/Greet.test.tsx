import "@testing-library/jest-dom/vitest";
// import { it, describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import Greet from "../../src/components/Greet";
import React from 'react'; // necessary, do not remove line


describe("Greet", () => {
  it("should render hi with the name when name is provided", () => {
    render(<Greet  />);
    const button = screen.getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent(/login/i);

  });
});

