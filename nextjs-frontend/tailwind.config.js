/** @type {import('tailwindcss').Config} */

const defaultTheme = require("tailwindcss/defaultTheme")

module.exports = {
  content: [
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        "Azeret": ["Azeret Mono", ...defaultTheme.fontFamily.sans],
        "JetBrains": ["JetBrains Mono", ...defaultTheme.fontFamily.sans],
        "Open-Sans": ["Open Sans", ...defaultTheme.fontFamily.sans],
        "Playfair": ["Playfair Display", ...defaultTheme.fontFamily.sans],
        "Quicksand": ["Quicksand", ...defaultTheme.fontFamily.sans],
      },
    },
  },
  plugins: [],
}

