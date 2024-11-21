import Section from "../components/Section"
import "../index.css"

export default function Index({ msg, width }) {
  return (
    <Section className={`bg-orange-200 flex flex-col justify-center items-center ${width}`}>
      <p className="text-2xl">∅</p>
      <p className="text-lg">{msg}</p>
    </Section>
  )
}