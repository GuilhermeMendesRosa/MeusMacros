/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}", // Inclua todos os arquivos do Angular
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: "#F3E8FF",
          100: "#E9D5FF",
          200: "#D8B4FE",
          300: "#C084FC",
          400: "#A855F7",
          500: "#9333EA",
          600: "#7E22CE",
          700: "#6B21A8",
          800: "#581C87",
          900: "#4A148C",
        },
      },
    },
  },
  plugins: [],
};
