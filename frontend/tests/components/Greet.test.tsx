import "@testing-library/jest-dom/vitest";
import { it, describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import Greet from "../../src/components/Greet";
import React from "react";

describe("Greet", () => {
  it("should render hi with the name when name is provided", () => {
    render(<Greet name="Melly" />);
    const heading = screen.getByRole("heading");
    // expect(heading).toBeInTheDocument();
    // expect(heading).toHaveTextContent(/Melly/i);
    expect(heading).
  });
});

/*
- falsche version von jest bzw 
*/
