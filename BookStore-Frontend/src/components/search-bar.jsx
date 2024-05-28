import '../css/search-bar.css'

export default function SearchBar(props) {
  return (
    <div className='SearchBar'>
      <input className='SearchBar-input' type="text" placeholder={props.placeholder} />
      <button className='SearchBar-submit' onClick={props.onsubmit}></button>
    </div>
  )
}