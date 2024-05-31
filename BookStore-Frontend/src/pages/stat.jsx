import MenuBar from '../components/menu-bar'
import { mode } from '../App'

export default function StatPage() {
  return (
    <div>
      <MenuBar index={2} mode={mode} />
    </div>
  )
}