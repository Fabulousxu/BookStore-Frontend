import '../css/search-bar.css'

export default function SearchBar(props) {
  return (
    <div className='SearchBar'>
      <input
        className='SearchBar-input'
        type="text"
        value={props.keyword}
        placeholder={props.placeholder}
        onChange={props.onChange}
        onKeyPress={e => { if (e.key === 'Enter') props?.onEnter() }}
      />
      <button className='SearchBar-submit' onClick={props?.onEnter}/>
    </div>
  )
}